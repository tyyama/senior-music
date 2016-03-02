#ifndef FILEPARSER_H
#define FILEPARSER_H

#include <vector>
#include <string>

using namespace std;


class FileParser
{
public:
    FileParser();
    bool isAudioFile(string file);
    vector<string> parse(string folder);

private:
    vector<string> musicLocations;
    vector<string> fileExtensions;
};

#endif // FILEPARSER_H
