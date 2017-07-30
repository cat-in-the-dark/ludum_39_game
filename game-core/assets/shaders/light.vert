//attribute vec3 a_position;
//attribute vec2 a_texCoord0;
//varying vec2 v_texCoords;
//varying vec2 fragPos;
//uniform vec2 screenSize;
//
//void main()
//{
//    vec3 pos = vec3(a_position.x / screenSize.x * 2.0, a_position.y  /  screenSize.y * 2.0, 0);
//    gl_Position = vec4(pos.xyz, 1);
//    fragPos = a_position.xy;
//    v_texCoords = a_texCoord0;
//}

//attribute vec4 a_position;
//attribute vec4 a_color;
//attribute vec2 a_texCoord0;
//uniform mat4 u_projTrans;
//varying vec4 v_color;
//varying vec2 v_texCoords;
//varying vec2 fragPos;
//uniform vec2 screenSize;
//
//void main()
//{
//   v_color = a_color;
//   v_color.a = v_color.a * (255.0/254.0);
//   v_texCoords = a_texCoord0;
//   gl_Position =  u_projTrans * a_position;
//
//   vec3 pos = vec3(a_position.x / screenSize.x * 2.0, a_position.y  /  screenSize.y * 2.0, 0);
//   fragPos = a_position.xy;
//}


attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 fragPos;
uniform vec2 screenSize;
void main()
{
   v_color = a_color;
   v_color.a = v_color.a * (255.0/254.0);
   v_texCoords = a_texCoord0;
   fragPos = vec2(a_position.x - screenSize.x / 2.0 , a_position.y - screenSize.y / 2.0);
   gl_Position =  u_projTrans * a_position;
}
