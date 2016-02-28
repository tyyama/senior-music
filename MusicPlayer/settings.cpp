#include "settings.h"
#include "ui_settings.h"
#include <string.h>

Settings::Settings(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Settings)
{
    ui->setupUi(this);
}

Settings::~Settings()
{
    delete ui;
}

void Settings::on_pushButton_clicked()
{
    QString dir = QFileDialog::getExistingDirectory(this, tr("Open Directory"),"/home",QFileDialog::ShowDirsOnly| QFileDialog::DontResolveSymlinks);
    std::string command = "parse.bat ";
    command +="\"";                                 //Enclose file directory in quotes to account for whitespace in names
    command += dir.toStdString();
    command +="\"";
    const char* c_command = command.c_str();        //Passing directory as argument to batch file
    std::system(c_command);                         //Run batch file with command prompt
}
