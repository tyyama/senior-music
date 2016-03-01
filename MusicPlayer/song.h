#ifndef SONG_H
#define SONG_H

#include <string>

using namespace std;

class Song
{
public:
    Song(string title, string artist, string albumName, string filePath);

    string title;
    string artist;
    string albumName;
    string filePath;
};

#endif // SONG_H
