//varying vec2 v_texCoords;
//varying vec2 fragPos;
//uniform sampler2D u_textureMain;
//uniform sampler2D u_textureCast;
//uniform vec2 mousePos;
//uniform vec2 screenSize;

//void main() {
//    float power = 200.0;
//    float exp = 0.002;
//    vec2 normalizedPos = vec2(mousePos.x - screenSize.x / 2.0, screenSize.y / 2.0 - mousePos.y);
//    float dist = length(fragPos - normalizedPos);
//
//    if(dist > power) {
//        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
//        return;
//    }
//
//    float k = exp * pow((power - dist) / 10.0, 2.0);
//    if(k > 0.4) k = 0.4;
//    k = k * 2.0;
//
//    int pre = 50;
//    vec2 fragPos2 = fragPos;
//    vec2 normalizedPos2 = normalizedPos;
//
//    vec2 dt = (fragPos2 - normalizedPos2) / float(pre);
//
//    // gl_FragColor = vec4(dt.xy, 0.0, 1.0);
//
//    vec2 coords = fragPos2 + screenSize / 2.0;
//    coords = vec2(coords.x, screenSize.y - coords.y);
//    coords = coords / screenSize;
//
//    vec4 c1 = texture2D(u_textureMain, v_texCoords);
//    int i;
//
//    for(i = 0; i < pre; i++) {
//        vec2 aprox = normalizedPos2 + screenSize / 2.0 + dt * float(i);
//        aprox = vec2(aprox.x, screenSize.y - aprox.y);
//        aprox = aprox / screenSize;
//
//        c1 *= texture2D(u_textureCast, aprox);
//    }
//
//    gl_FragColor = c1 * k;
//}
//void main() {
//    //gl_FragColor = vec4(1.0, 0.0, 1.0, 1.0);
//    gl_FragColor = texture2D(u_textureMain, v_texCoords);
//}

//#ifdef GL_ES
//#define LOWP lowp
//precision mediump float;
//#else
//#define LOWP
//#endif
//varying LOWP vec4 v_color;
//varying vec2 v_texCoords;
//varying vec2 fragPos;
//uniform sampler2D u_texture;
//void main()
//{
//  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
//}

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying vec2 fragPos;
uniform sampler2D u_texture;
uniform sampler2D u_textureCast;
uniform sampler2D u_textureBack;
uniform vec2 mousePos;
uniform vec2 screenSize;


//void main()
//{
//  //gl_FragColor = (texture2D(u_texture, v_texCoords) + texture2D(u_textureCast2, v_texCoords)) / 2.0;
//  gl_FragColor = texture2D(u_textureCast, v_texCoords);
//}

void main() {
    float power = 400.0;
    float exp = 0.002;
    vec2 normalizedPos = vec2(mousePos.x - screenSize.x / 2.0, screenSize.y / 2.0 - mousePos.y);
    float dist = length(fragPos - normalizedPos);

    if(dist > power) {
        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
        return;
    }

    float k = exp * pow((power - dist) / 10.0, 2.0);
    if(k > 0.4) k = 0.4;
    k = k * 3.0;

    int pre = 50;
    vec2 fragPos2 = fragPos;
    vec2 normalizedPos2 = normalizedPos;

    vec2 dt = (fragPos2 - normalizedPos2) / float(pre);

    //gl_FragColor = texture2D(u_textureCast, v_texCoords);
    //return;

    vec2 coords = fragPos2 + screenSize / 2.0;
    coords = vec2(coords.x, screenSize.y - coords.y);
    coords = coords / screenSize;

    vec4 c1 = texture2D(u_texture, v_texCoords);
    if(c1.r == 0.0 && c1.g == 0.0 && c1.b == 0.0 && c1.a == 0.0)
        c1 = texture2D(u_textureBack, v_texCoords);
    int i;

    for(i = 0; i < pre; i++) {
        vec2 aprox = normalizedPos2 + screenSize / 2.0 + dt * float(i);
        //aprox = vec2(aprox.x, screenSize.y - aprox.y);
        aprox = aprox / screenSize;

        c1 *= texture2D(u_textureCast, aprox);
    }

    gl_FragColor = c1 * k;
}