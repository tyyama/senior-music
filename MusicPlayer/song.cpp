#include "song.h"
#include <string>

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
    TagLib::FileRef f("D:\\Libraries\\Music\\iTunes\\iTunes Media\\Music\\The Black Keys\\El Camino\\03 Gold On the Ceiling.m4a");
}
