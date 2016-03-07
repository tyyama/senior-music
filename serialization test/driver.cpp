#include "serializer.h"
#include "song.cpp"
#include "album.cpp"
#include <cstring>
int main(){
	/*Song test;
	Serializer<Song> a;
	strcpy(test.name,"hellllloooooooo worrrrrrlllld");
	a.serialize(test,"test.dat");*/
	
	Song loadedSong;
	Serializer<Song> b;
	loadedSong = b.deserialize("test.dat");
	cout<<loadedSong.name<<endl;
	
	Serializer<Album> c;
	Album al;
	al.addTrack(loadedSong);
	c.serialize(al,"test2.dat");
}