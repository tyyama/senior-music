#include <string>
#pragma once
using namespace std;

class Song{
	
	public:
		char name[512];
		char artist[512];
		char path[1024];
		string getName(){
			return string(name);
		}
		string getArtist(){
			return string(artist);
		}
		string getPath(){
			return string(path);
		}
};