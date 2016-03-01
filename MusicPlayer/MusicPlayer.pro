#-------------------------------------------------
#
# Project created by QtCreator 2016-02-13T14:56:23
#
#-------------------------------------------------

QT       += core gui multimedia multimediawidgets

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = MusicPlayer
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    settings.cpp \
    song.cpp

HEADERS  += mainwindow.h \
    settings.h \
    song.h

FORMS    += mainwindow.ui \
    settings.ui

RESOURCES += \
    resources.qrc

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/taglib/lib/release/ -ltaglib
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/taglib/lib/debug/ -ltaglib_d
else:unix: LIBS += -L$$PWD/taglib/lib/ taglib

INCLUDEPATH += $$PWD/taglib/include/
INCLUDEPATH += $$PWD/taglib/include/ape/
INCLUDEPATH += $$PWD/taglib/include/flac/
INCLUDEPATH += $$PWD/taglib/include/mpc/
INCLUDEPATH += $$PWD/taglib/include/mpeg/
INCLUDEPATH += $$PWD/taglib/include/mpeg/id3v1/
INCLUDEPATH += $$PWD/taglib/include/mpeg/id3v2/
INCLUDEPATH += $$PWD/taglib/include/mpeg/id3v2/frames/
INCLUDEPATH += $$PWD/taglib/include/ogg/
INCLUDEPATH += $$PWD/taglib/include/ogg/flac/
INCLUDEPATH += $$PWD/taglib/include/ogg/vorbis/
INCLUDEPATH += $$PWD/taglib/include/toolkit/

DEPENDPATH += $$PWD/taglib/include/

CONFIG += c++11
