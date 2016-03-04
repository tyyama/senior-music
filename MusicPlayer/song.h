#ifndef SONG_H
#define SONG_H

#include <string>
#include <QMediaPlayer>
#include <id3v2tag.h>
#include <mpegfile.h>
#include <id3v2frame.h>
#include <id3v2header.h>
#include <attachedpictureframe.h>
#include <cstdio>
#include <fileref.h>

using namespace std;

class Song
{
public:
    Song(string pFilePath);

    char title[200];
    char artist[200];
    char album[200];
    char filePath[500];

    void findMetadata();
};

#endif // SONG_H
