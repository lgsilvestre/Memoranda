matias Branch
  
  
  
                          Memoranda version 1.0-RC3.1

================================================================================

Release notes
-------------

1.0-RC3.1 is a bugfix release for 1.0-RC3 (Release Candidate 3)  
  

Download
--------
 
Memoranda is distributed as a single zip-file containing the binaries, 
sourcecode and all dependency libraries.

The latest release can be downloaded from 
http://sourceforge.net/project/showfiles.php?group_id=90997

Contents
--------
Included in the release are the following:

    README.txt        this file 
    BUILD.txt      Building instructions
    FAQ.txt           Frequently Asked Questions 
    CHANGES.txt       Release changes log
    LICENSE           GPL software license
    memoranda.bat     Memoranda launch script for Win32 
    memoranda.sh      Memoranda launch script for Unix (bash)
    build.xml         Build configuration for Jakarta Ant
    build/            Contains the prebuilt 'memoranda.jar'
    lib/              Dependency libraries and other resources
    src/              Memoranda source code (not in the binary distribution)


System requirements
-------------------
For running Memoranda you need a Java 2 Runtime Environment (JRE) with a 
version number >= 1.4.0.  You can download the JRE for a number of platforms 
from http://java.sun.com. 

The "System tray icon" feature is available for 

    * MS Windows
    * K Desktop Environment (KDE) >3.0

Memoranda has been tested on Windows 2000, XP + j2re-1.4.0, 1.4.1, 1.4.2, 1.5
Linux + KDE + j2re-1.4.0, 1.4.1_01, 1.5.


Installation
------------
ALL PLATFORMS:
Unpack the downloaded zip file into a dir of your choice. It will 
create there the "memoranda**-**" subdirectory that will be known 
as the "Memoranda home dir".

IMPORTANT: JRE executables directory (jre/bin) must be on your system PATH.

*NIX:
Cd to Memoranda directory and execute:

chmod 755 ./memoranda.sh
chmod 755 ./lib/kde/systray4jd

KDE USERS: 
You optionally can put the systray4jd daemon in your system autostart and remove 
its startup line from memoranda.sh. 

NON-KDE USERS: 
You can safely remove the systray4j startup line from memoranda.sh.

Running
-------

    1) Go to the Memoranda home dir. 
    2) Run the 'memoranda.bat' (on Windows) or './memoranda.sh' (on Unix)
Enjoy!

NOTE: To launch Memoranda in minimized mode (system tray only), use the "-m" 
option. You can also set this behavior in File->Preferences menu.


Memoranda on the Net
--------------------

    * Memoranda homepage: http://memoranda.sf.net

    * Memoranda project on SourceForge: http://sf.net/projects/memoranda

Mailing lists
-------------

    * Main discussion list: memoranda-list@lists.sourceforge.net
      Subscribe: 
          https://lists.sourceforge.net/lists/listinfo/memoranda-list

        * Announce list: memoranda-announce@lists.sourceforge.net
      Subscribe: 
          https://lists.sourceforge.net/lists/listinfo/memoranda-announce

      
Contributors
------------

    * Alex Alishevskikh <alexeya@users.sourceforge.net>
    * Patrick Bielen    <pbielen(at)users.sourceforge.net>  
    * Ryan Ho <rawsushi(at)users.sourceforge.net>
    * Ivan Ribas   <ivanrise(at)msn.com>  
    * Jyrki Velhonoja <velhonoja (at) kapsi.fi>
    * Jeremy Whitlock <whitlock(at)starprecision.com>

If you want to get involved please email us.


Legal notices
-------------
Copyright (c) 2002-2007 Memoranda project team.
All rights reserved.

This product is licensed under the terms and conditions of the 
GNU General Public License (see LICENSE). NO WARRANTY.


Dependencies (./lib directory)
------------------------------

[lib/xom-1.0.jar]
   XOM (XML Object Model) API
    Copyright (c) 2003 Elliotte Rusty Harold. All Rights Reserved. 
    License: LGPL (see lib/xom.LICENSE)    
    Version: 1.0d21 
    Required

[lib/xmlParserAPIs.jar]
[lib/xercesImpl.jar]
   Apache Xerces XML parser
    Copyright (c) 1999-2002 The Apache Software Foundation. 
    All Rights Reserved. This product includes software developed 
    by the Apache Software Foundation (http://www.apache.org/).
    License: Apache Software License (see lib/xerces.LICENSE)
    Version: 2.1.0
    Required

[lib/systray4j.jar]
[lib/win32/systray4j.dll]
[lib/kde/systray4jd]
   SysTray for Java
    (c) 2003 SnoozeSoft (http://www.eikon.tum.de/~tamas/)
    License: LGPL (see lib/systray4j.LICENSE)
    Version: 2.4.1
    Required

[nekohtml.jar]
[nekohtmlXni.jar]
   CyberNeko HTML Parser
    (C) Copyright 2002-2003, Andy Clark. All rights reserved. 
    This product includes software developed by Andy Clark.
    License: The CyberNeko Software License, Version 1.0 
    (see lib/nekohtml.LICENSE)
    Version: 0.7.6
    Optional - required for XHTML export only.

[lib/kunststoff.jar]
   Kunststoff look-and-feel library
    Copyright (c) INCORS GmbH (www.incors.com)
    License: LGPL (see lib/kunststoff.LICENSE)
    Version: 2.0.1
    Optional

Some icons are taken from the Connectiva Crystal icon theme for KDE, 
(c) Everaldo (www.everaldo.com)
     
Thanks to everybody working on third-party products.

The Memoranda team, 
2007

================================================================================
$Id: README.txt,v 1.11 2007/04/06 21:21:21 alexeya Exp $