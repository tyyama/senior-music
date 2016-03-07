template <class Object>
Serializer<Object>::Serializer(){
	
}

template <class Object>
void Serializer<Object>::serialize(Object obj, string file){
	ofstream ofs(file.c_str(),ios::binary);
	ofs.write((char*)&obj,sizeof(obj));
	ofs.close();
}

template <class Object>
void Serializer<Object>::serialize(Object obj, const char* file){
	ofstream ofs(file,ios::binary);
	ofs.write((char*)&obj,sizeof(obj));
	ofs.close();
}

template <class Object>
Object Serializer<Object>::deserialize(const string &path){
	int fileSize = getFileSize(path);
	bool holder[fileSize];
	Object* temp = (Object*)&holder[0];
	ifstream ifs(path.c_str(),ios::binary);
	ifs.read((char*)&holder,fileSize);
	return *temp;
}

template <class Object>
Object Serializer<Object>::deserialize(const char* path){
	int fileSize = getFileSize(path);
	bool holder[fileSize];
	Object* temp = (Object*)&holder[0];
	ifstream ifs(path,ios::binary);
	ifs.read((char*)&holder,fileSize);
	return *temp;
}

template <class Object>
int Serializer<Object>::getFileSize(string &file){
	ifstream in(file.c_str(), std::ifstream::ate | std::ifstream::binary);
    return (int) in.tellg();
}

template <class Object>
int Serializer<Object>::getFileSize(const char* file){
	ifstream in(file, std::ifstream::ate | std::ifstream::binary);
    return (int) in.tellg();
}