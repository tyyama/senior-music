#include "fileparser.h"
#include <vector>
#include <string>
#include <Windows.h>
#include <iostream>

using namespace std;

FileParser::FileParser()
{
    fileExtensions.push_back(".flac");
    fileExtensions.push_back(".mp3");
    fileExtensions.push_back(".m4a");
    fileExtensions.push_back(".wav");

    musicLocations.push_back("D:\\Libraries\\Music\\iTunes\\iTunes Media\\Music\\The Black Keys");
}

bool FileParser::isAudioFile(string file) {
    bool hasEnding = false;
    for (vector<string>::iterator it = fileExtensions.begin(); it != fileExtensions.end(); ++it) {
        if (file.length() >= (*it).length()) {
               hasEnding = (0 == file.compare (file.length() - (*it).length(), (*it).length(), (*it)));
               if (hasEnding)
                   return true;
        }
    }

    return false;
}

vector<string> FileParser::parse(string folder) {
    vector<string> songPaths;

    char search_path[500];
    sprintf(search_path, "%s\\*.*", folder.c_str());
    WIN32_FIND_DATA fd;
    HANDLE hFind = ::FindFirstFile(search_path, &fd);
    if(hFind != INVALID_HANDLE_VALUE) {
        do {
            // read all (real) files in current folder
            // , delete '!' read other 2 default folder . and ..

            if (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
                if (strcmp(fd.cFileName, ".") && strcmp(fd.cFileName, "..")) {
                    string fullPath = folder;
                    fullPath += "\\";
                    fullPath += fd.cFileName;
                    //cerr << "Full Path: " << fullPath << endl;
                    vector<string> folderSongs = parse(fullPath);
                    for (vector<string>::iterator it = folderSongs.begin(); it < folderSongs.end(); ++it) {
                        songPaths.push_back(*it);
                    }
                }
            }

            if(! (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) ) {
                if (isAudioFile(fd.cFileName)) {
                    string fullPath = folder;
                    fullPath += "\\";
                    fullPath += fd.cFileName;
                    //cerr << fullPath << endl;//names.push_back(fd.cFileName);
                    songPaths.push_back(fullPath);
                }
            }
        }while(::FindNextFile(hFind, &fd));
        ::FindClose(hFind);
    }

    return songPaths;
}
