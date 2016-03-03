#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "settings.h"
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include "fileparser.h"
#include "song.h"
/*#include <id3v2tag.h>
#include <mpegfile.h>
#include <id3v2frame.h>
#include <id3v2header.h>
#include <attachedpictureframe.h>
#include <cstdio>*/


using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    player = new QMediaPlayer(this);
    //vw = new QVideoWidget(this);
    //player->setVideoOutput(vw);
    //this->setCentralWidget(vw);
    //QWidget::showFullScreen();

    slider = new QSlider(this);
    //bar = new QProgressBar(this);

    slider->setOrientation(Qt::Horizontal);
    ui->statusBar->addPermanentWidget(slider);
    //ui->statusBar->addPermanentWidget(bar);

    connect(player, &QMediaPlayer::durationChanged,slider,&QSlider::setMaximum);
    connect(player, &QMediaPlayer::positionChanged,slider,&QSlider::setValue);
    connect(slider, &QSlider::sliderMoved,player,&QMediaPlayer::setPosition);

    /*connect(player, &QMediaPlayer::durationChanged,bar,&QProgressBar::setMaximum);
    connect(player, &QMediaPlayer::durationChanged,bar,&QProgressBar::setValue);*/

    refresh_music();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_actionOpen_triggered()
{
    QString filename = QFileDialog::getOpenFileName(this, "Open a File","","Audio File (*.mp3)" ); //Chooses specified file type
    on_actionStop_triggered();

    player->setMedia(QUrl::fromLocalFile(filename));

    on_actionPlay_triggered();
}

void MainWindow::on_actionPlay_triggered()
{
   player->play();
   ui->statusBar->showMessage("Playing");
}

void MainWindow::on_actionPause_triggered()
{
  player->pause();
  ui->statusBar->showMessage("Paused");

}

void MainWindow::on_actionStop_triggered()
{
    player->stop();
    ui->statusBar->showMessage("Stopped");
}

/*void MainWindow::on_actionExit_triggered()
{
    this->close();
}*/

/*void MainWindow::on_sliderProgress_sliderMoved(int position)
{

}

void MainWindow::on_sliderVolume_sliderMoved(int position)
{

}

void MainWindow::on_durationChanged(qint64 position)
{

}

void MainWindow::on_positionChanged(qint64 position)
{

}*/



void MainWindow::on_actionSettings_triggered()
{
    Settings settings;
    settings.setModal(true);
    settings.exec();
}

void MainWindow::refresh_music() {
    FileParser fp;
    string filePath = "D:\\Libraries\\Music\\iTunes\\iTunes Media\\Music\\The Black Keys";//\\El Camino";
    //string filePath = "test";
    cerr << "****" << endl;
    vector<Song> songs;
    vector<string> songPaths = fp.parse(filePath);

    for (vector<string>::iterator it = songPaths.begin(); it < songPaths.end(); ++it) {
        Song song(*it);
        song.findMetadata();
        songs.push_back(song);
        cerr << *it << endl;
    }
    cerr << "****" << endl;

    for (vector<Song>::iterator it = songs.begin(); it < songs.end(); ++it) {
        cerr << (*it).filePath << endl;
    }


    // get potential music locations
    /*string fileLocations = "";
    fileLocations.append("\"D:\\Libraries\\Music\\iTunes\\iTunes Media\\Music\\The Black Keys\"");
    //fileLocations.append("C:\\Users\\Vocaloid\\Music");

    // structure command for std::system (may take more than one argument later)
    char command[500] = "parse.bat ";
    int curIndex = 10;

    int fileLocationsSize = fileLocations.size();
    for (int i = 0; i <= fileLocationsSize; i++) {
        command[curIndex] = fileLocations[i];
        curIndex++;
    }

    cerr << command << endl;
    system(command);

    // check if the file has been created
    ifstream infile("Songs.dat");
    bool fileExists = infile.good();
    int count = 0;
    while (fileExists != 1) {
        infile.close();
        ifstream infile("Songs.dat");
        fileExists = infile.good();
        cerr << "ran" << count << endl;
        count++;
    }

    // loops through output file from parse.bat
    cerr << infile.good() << endl;
    infile.close();
    get_metadata();*/
}
