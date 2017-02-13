The below info might be outdated - I have not ran the script via vagrant/ansible/VirtualBox for quite some time

# CenturyLink Speed Finder using PhantomJS headless browser

Instructions on how to set an environment up to run this program:

* Clone this repository
* Download/install Vagrant, Ansible and VirtualBox
  * Vagrant: https://www.vagrantup.com/downloads.html
  * Ansible: http://docs.ansible.com/ansible/intro_installation.html
    * note on Ansible install - it's very simple if you use pip for python to install it
  * VirtualBox: https://www.virtualbox.org/wiki/Downloads
* Modify the Vagrantfile at Resources/vagrant/Vagrantfile and tweak the CPU and RAM. The defaults are fine, but if you don't have enough memory/CPU cores, you might want to lower them
* Navigate to Resources/vagrant and type: "vagrant up" without quotes
  * This should kick off the automated installation process of everything. Depending on your internet connection speed and CPU specs, it could be anywhere from a few minutes to a few hours.
  

Once your Vagrant vm is running and the Ansible script is done (with no errors, hopefully):
* type "vagrant ssh" to remote into your Vagrant machine that was just created
* navigate to the clspeed directory
* load addresses into rabbitmq:
  *"python load_rabbitmq.py mega_addresses_final"
* run the jar file
  *"java -jar CLSpeedPhantomJS.jar"
  
You should see a whole bunch of output from phantomjs. It'll churn for a while and between piles of phantomjs output, you should see success messages for addresses being checked, like so:

```
  phantomjs://platform/console++.js:263 in error
[ERROR - 2016-05-11T18:14:23.218Z] Session [25ac8f00-17a4-11e6-ae6a-cb2d41f5ca85] - page.onError - stack:
  (anonymous function) (https://shop.centurylink.com/assets/js/geoamapi.js:29)
  n (https://shop.centurylink.com/qcms/qCmsRepository/FreeRange/shop/optimized/js/lqNcBundleJsOptimized_IMPLEMENTED.js:239)
  fireWith (https://shop.centurylink.com/qcms/qCmsRepository/FreeRange/shop/optimized/js/lqNcBundleJsOptimized_IMPLEMENTED.js:241)
  ready (https://shop.centurylink.com/qcms/qCmsRepository/FreeRange/shop/optimized/js/lqNcBundleJsOptimized_IMPLEMENTED.js:230)
  B (https://shop.centurylink.com/qcms/qCmsRepository/FreeRange/shop/optimized/js/lqNcBundleJsOptimized_IMPLEMENTED.js:239)

  phantomjs://platform/console++.js:263 in error
20: 8 wellesly place, minneapolis, MN 55436
1000: 8 mill rd, hopkins, MN 55305
[INFO  - 2016-05-11T18:14:25.749Z] ShutdownReqHand - _handle - About to shutdown
12: 8 sunfish ln, st paul, MN 55118
[INFO  - 2016-05-11T18:14:26.052Z] ShutdownReqHand - _handle - About to shutdown
[INFO  - 2016-05-11T18:14:26.357Z] ShutdownReqHand - _handle - About to shutdown
[INFO  - 2016-05-11T18:14:26.442Z] GhostDriver - Main - running on port 22033
20: 8 wellesley pl, minneapolis, MN 55436
```

with the "success messages" being in the form of {SPEED}: {ADDRESS}

To see the results: 
* log into phpmyadmin by navigating to http://localhost:8180/phpmyadmin on your machine running the VM (not the actual VM itself)
  * username: clspeed
  * password: clspeed
* Once logged in, expand the clspeed item on the left-hand panel, then click on clspeed_{YEAR}\_{MONTH}\_{DAY}
![clspeed table image](http://i.imgur.com/mRC5Pck.jpg)
* This should open up the table with the results. 

To export the gigabit addresses to a CSV, do the following:
* Click on the SQL tab at the top of the screen
* In the SQL box, type:
```SELECT * FROM `clspeed_2016_05_11` WHERE speed = '1000.0'```
  * Be sure to change the table name in the sql above to your actual table name
* Click the "Go" button
* You should now be back at your table page but only showing addresses with a speed value of 1000.0
* In the "Query results operations" box, click on Export
* In the Export screen: 
  * Choose "Custom - display all possible options"
  * Select "CSV" from the Format drop-down menu (NOT CSV for MS Excel)
  * Check the box that says "Put columns names in the first row"
  * Click "Go" button - this will download your CSV
  
To generate a map:
* Open your CSV in a spreadsheet program. 
  *I have used MS Excel, LibreOffice Calc and Google Sheets for this part. Any spreadsheet program should work.
  *Be sure to tell the spreadsheet program to use comma delimited columns when opening your CSV
* Select all rows/columns in the spreadsheet (cmd+a / ctrl+a) and copy (cmd+c / ctrl+c)
* Navigate to http://www.easymapmaker.com/
* In the box at top that says "Click here to paste data", click in it then paste your data
* Since we're only mapping one speed (1000.0), we can ignore having multiple colors for markers
* Unselect "enable clustering" 
* Underneath the box where you pasted your data, click on the "Make Map" button
* You should now see a map with all your points on it. 
* Click on the "Launch Map Save" button below
  * Give it a title of some sort
  * Make it public if you wish to share the map with other people
* Click "Save Map"
* You'll be brought back to the previous page, and it will show you the URL for your new map.
  * I highly suggest you click on the "Edit" button next to the URL and give it a URL which is easier to remember 
  
That's it - you can share the URL to your map

