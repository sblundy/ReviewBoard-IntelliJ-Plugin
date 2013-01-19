IntelliJ Client Plugin for ReviewBoard
=============
The point of this plugin is to ease interacting with a ReviewBoard server from the IntelliJ IDE. It is not
intended to be a full replacement for the web-ui. Rather it will simplify using IntelliJ for what it does best: viewing
code.

Project Setup
-------------

Required Plugins:

* Plugin DevKit
* QAPlug
* QAPlug - Checkstyle
* QAPlug - FindBugs
* QAPlug - PMD
* TestNG-J
* UI Designer

JDK:

You'll need to setup the appropriate SDK. It has to be an IntelliJ SDK because we're dependent upon
The many libraries and services it provides. 

Currently we're developing against version 11.1.3

1. Go to File -> Project Structure
1. Click on SDKs
1. Click on plus icon at top of second pane -> IntelliJ IDEA Plugin SDK
1. Browse to home of IntelliJ IDEA 11.1.3
    * It should be named 'IDEA-IU-117.798'
1. Click Ok

Global Libraries:

* org.testng:testng:6.8
* org.easymock:easymock:3.1
