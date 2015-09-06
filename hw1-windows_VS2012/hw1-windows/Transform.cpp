// Transform.cpp: implementation of the Transform class.


#include "Transform.h"
#include <stdio.h>

//Please implement the following functions:

// Helper rotation function.  
mat3 Transform::rotate(const float degrees, const vec3& axis) {
	float d = sqrt(pow(axis.x, 2) + pow(axis.y, 2) + pow(axis.z, 2));
	printf("axis before: %.2f, %.2f, %.2f; distance: %.2f\n", axis.x, axis.y, axis.z, d);
	const float theta = glm::radians(degrees);
	mat3 dual = mat3(0., axis.z, -axis.y, 
		            -axis.z, 0., axis.x, 
		            axis.y, -axis.x, 0.0);
	mat3 am = mat3(axis.x, axis.y, axis.z, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	mat3 I = mat3();
	I[0][0] = 1.0;
	I[1][1] = 1.0;
	I[2][2] = 1.0;
    mat3 result = I * cos(theta) + am * glm::transpose(am) * (1.0 - cos(theta)) + dual * sin(theta);

  // You will change this return call
  return result;
}

// Transforms the camera left around the "crystal ball" interface
void Transform::left(float degrees, vec3& eye, vec3& up) {
	float d = sqrt(pow(eye.x, 2) + pow(eye.y, 2) + pow(eye.z, 2));
	printf("Eye before: %.2f, %.2f, %.2f; distance: %.2f\n", eye.x, eye.y, eye.z, d);
	printf("Up before: %.2f, %.2f, %.2f; distance: %.2f\n", up.x, up.y, up.z, sqrt(pow(up.x, 2) + pow(up.y, 2) + pow(up.z, 2)));
	eye = rotate(degrees, glm::normalize(up)) * eye;
	printf("Coordinates: %.2f, %.2f, %.2f; distance: %.2f\n", eye.x, eye.y, eye.z, d);
}

// Transforms the camera up around the "crystal ball" interface
void Transform::up(float degrees, vec3& eye, vec3& up) {
	float d = sqrt(pow(eye.x, 2) + pow(eye.y, 2) + pow(eye.z, 2));
	float upd = sqrt(pow(up.x, 2) + pow(up.y, 2) + pow(up.z, 2));
	vec3 axis = glm::normalize(glm::cross(eye, up));
	eye = rotate(degrees, axis) * eye;
	up = rotate(degrees, axis) * up;
	printf("Coordinates: %.2f, %.2f, %.2f; distance: %.2f\n", eye.x, eye.y, eye.z, sqrt(pow(eye.x, 2) + pow(eye.y, 2) + pow(eye.z, 2)));
}

// Your implementation of the glm::lookAt matrix
mat4 Transform::lookAt(vec3 eye, vec3 up) {
	printf("eye: %.2f, %.2f, %.2f; distance: %.2f\n", eye.x, eye.y, eye.z, sqrt(pow(eye.x, 2) + pow(eye.y, 2) + pow(eye.z, 2)));
	printf("up: %.2f, %.2f, %.2f; distance: %.2f\n", up.x, up.y, up.z, sqrt(pow(up.x, 2) + pow(up.y, 2) + pow(up.z, 2)));
	vec3 w = glm::normalize(eye);
	vec3 u = glm::normalize(glm::cross(up, w));
	vec3 v = glm::cross(w, u);

	mat4 result = mat4(u.x, v.x, w.x, 0.0,
		u.y, v.y, w.y, 0.0,
		u.z, v.z, w.z, 0.0,
		-eye.x*u.x-eye.y*u.y-eye.z*u.z, -eye.x*v.x - eye.y*v.y - eye.z*v.z, -eye.x*w.x - eye.y*w.y - eye.z*w.z, 1.0);

  // You will change this return call
  return result;
}

Transform::Transform()
{

}

Transform::~Transform()
{

}
