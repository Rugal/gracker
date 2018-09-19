# Gracker - Git based Issue Tracker

[![Build Status](https://travis-ci.org/Rugal/gracker.svg?branch=master)](https://travis-ci.org/Rugal/gracker)


## Terminology

[![Build Status](./configuration/StorageStructure.svg)](https://travis-ci.org/Rugal/gracker)


### id
The id of issue, must be the same name as `reference`.  

### reference
The actual existence in `git`, similar to `branch`, we use it as tracking object for individual issue.  
The name of reference is used as `id`.  

The content of which will point to the `latest commit` object id.  

### commit
The actual existence in `git`, this contains basic commit information, including  
1. `tree` object id  
2. Committer, time  
3. Author, time  
4. `status`  

### tree
The actual existence in `git`, this contains  
1. title  
2. body  
3. label  

### blob
The actual existence in `git`, this contains the actual content in byte array format.  
Everything like title, body, label, tree, commit are in this format.  

## Log Level

### TRACE
Doesn't contain request specific information, usually used as tracing log to tell where the code went through.
### DEBUG
Does contain request specific information so as to help debugging.
### INFO
Does contain request specific information, this is not just for debugging but also let user know what's happening.
### WARN
Code logic might deviate from its normal flow, but still under control.
### ERROR
Code logic is out of control and needs to let user know what's happening.