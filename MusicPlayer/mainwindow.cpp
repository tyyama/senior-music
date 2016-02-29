#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "settings.h"

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
  std::system("parse.bat C:\\Users\\Vocaloid\\Music\\");
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
