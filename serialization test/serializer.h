#include <iostream>
#include <fstream>
#include <string>
#include <ios>

using namespace std;

template <class Object>
class Serializer{
	public:
		Serializer();
		void serialize(Object obj, string file);
		void serialize(Object obj, const char* file);
		Object deserialize(const string &path);
		Object deserialize(const char* path);
	private:
		int getFileSize(string &file);
		int getFileSize(const char* file);
};

#include "serializer.cpp.h"