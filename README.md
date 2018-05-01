This is plugin written in Intellij Idea for my project. Goal of this plugin is to integrate some of the Java decompilers in the Android Studio.

### Setting up development environment for development/testing

In order to test the plugin you need to have Intellij Idea (Community Edition) installed and set up appropriately. First thing you will need to do, is to download and install Intellij Idea, Community Edition perferably, since it is open source. After you have installed the Intellij Idea, you will need its source code also, since many of its components are used in the plugin development. You should check out the source code from git, for example by executing the following command:

git clone --depth 1 git://git.jetbrains.org/idea/community.git target_directory

You can optionally build the code directly from Intellij Idea or from command line by running the ant script build.xml but this step is not necessary and you can skip it. For the next thing you will need to create a new SDK which will point to installation of the JDK 1.8. You can do that by going to the File > Project Structure and then under Platform Settings > SDK add new SDK by specifying the home directory of JDK 1.8. Now in the same menu on tab Project Settings > Project you need to create new Intellij Platform SDK, specify the installation of Intellij Idea as Home directory and select the already set-up JDK as the default Java SDK. Now go back to the Platform Settings > SDK and in the tab Sourcepath add the directory in which you have checked out the source code of Intellij Idea.

If you want to develop this plugin, after importing it in Intellij Idea, you will need to specify the created SDK as project SDK in project settings (File > Project Structure > Project > Project SDK). Also in order to plugin to work with decompilers and dex2jar you will need to add their jars to referenced libraries in project (Project Structure > Libraires).

All the information about plugin development, setting up environment, building the plugin and all the good stuff can be found [here](https://www.jetbrains.org/intellij/sdk/docs/basics/getting_started.html)

### Installing the plugin

If you want to install the plugin in your IDE, you will need to import plugin jar/zip file from the disk. This can be done in these steps: File > Settings > Plugins > Install plugin from disk and then select the plugin file.

### Using the plugin

Supported tools:
- [Jadx](https://github.com/skylot/jadx)
- [Procyon](https://bitbucket.org/mstrobel/procyon)
- [FernFlower](https://github.com/fesh0r/fernflower)
- [Dex2Jar](https://github.com/pxb1988/dex2jar)

Plugin creates two menu items: one for defining which decompilers to use and their arguments, and second one which will start decompiling as background task. The apk file will be detected from the project directory, if the apk file is not present the plugin actions will be disabled. Results of the decompilation will be in the decompiled directory of the project.
