#include "song.h"
#include <string>
#include <iostream>

using namespace std;

Song::Song(string pFilePath)
{
    int pFilePathSize = pFilePath.size();
    for (int a = 0; a <= pFilePathSize; a++) {
        filePath[a] = pFilePath[a];
    }
}

void Song::findMetadata() {
    //TagLib::FileRef f(filePath);
    //TagLib::FileRef f("D:\\Libraries\\Music\\iTunes\\iTunes Media\\Music\\The Black Keys\\El Camino\\03 Gold On the Ceiling.m4a");

    // temporary code to strip song name from file path
    string filename = filePath;
    string songName = "";
    // find the last occurrence of '\\'
    size_t pos = filename.find_last_of("\\");
    // make sure the position is valid
    if (pos != string::npos)
        songName = filename.substr(pos+1);
    else
        cout << "Coud not find \\ in the string\n";

    int songNameSize = songName.size();
    for (int a = 0; a <= songNameSize; a++) {
        title[a] = songName[a];
    }
}
