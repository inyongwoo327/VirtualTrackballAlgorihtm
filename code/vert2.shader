//Project 1
//Example vertex shader
#version 410

// Input from VB0s 0, 1 and 2
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 tex_coord;
layout (location = 2) in vec3 normal;

// Output to fragment shader
out vec2 tc;
out vec3 n;
out vec4 varyingColor;
out vec3 varyingViewDir;

// Input constant across all vertices
uniform mat4 mv_matrix;
uniform mat4 proj_matrix;
uniform sampler2D samp;

void main(void)
{	// Standard matrix transformation of vertex position
    gl_Position = proj_matrix * mv_matrix * vec4(position,1.0);
	
	// Pass texture coordinate on to fragment shader
	tc = tex_coord;
	
	// Transform normal to camera coordinates
	n = (mv_matrix*vec4(normal,1.0f)).xyz;
	// Create a view vector to the camera location (0,0,2)
	varyingViewDir = (mv_matrix*vec4(position-vec3(0.0f,0.0f,2.0f),1.0f)).xyz;
	
	// Create a color based on vertex position
	varyingColor = vec4(position*0.4,1.0) + vec4(0.5, 0.5, 0.5, 0);
}