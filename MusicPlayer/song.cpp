#include "song.h"
#include <string>

using namespace std;

Song::Song(string pTitle, string pArtist, string pAlbumName, string pFilePath)
{
    title = pTitle;
    artist = pArtist;
    albumName = pAlbumName;
    filePath = pFilePath;
}
