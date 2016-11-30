# food-reviews

Test task implementation

## Run locally

Download `Reviews.csv` from https://www.kaggle.com/snap/amazon-fine-food-reviews and copy to project's root folder.

To run application:
`run_wo_translate.sh`

## Features

Implemented: 
* find most active users
* find most commented food items
* find most used words. Known limitation is code treats html tags like ordinal words.

Not implemented:
* translate reviews. Code contains description of possible implementation but we don't have enough time to finish it.

## Advanced features

- _We are interested in using full multi core CPU power_. 
    Spark's configurated to use all available CPU cores (`local[*]`).

- _We will be running this on machine with 500MB of RAM. How do you make sure that we are not using more than that? How are you going to monitor the memory usage of your program?_ 
    Spark cannot use more memory than OS allocated for java process. So usage `-Xmx` for our application is the best solution (or `-mem` for `sbt` apllication). 
    If we wanted to monitor memory usage programmatically, we'd use `java.lang.Runtime` possibilities.  

- _Our goal is to support the files with up to 100M reviews on multiple machines with 500MB of RAM and 4 core CPUs. How are you going to make it happen?_
    Spark supports running in cluster mode. To achieve it, we need to set up `master url` for all application instances.
