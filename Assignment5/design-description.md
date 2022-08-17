# Omega Kwanga
#### CS-6300: Software Dev Process
#### Assignment 5: Software Design
#### June 2022

## Intro
This software "Job Ranker" will be used to compare job offers with benefits, in different locations, and other aspects beyond salary.


## Justification and Assumptions 

When a user lunches the application, he is presented with the options to login or signup. Login will be via email and password.

The Static User Class will house the user information. 

Once logged in, a user has the option of comparing multiple jobs based on a variety of criteria and filters. A user can compare both current job and job offers, or in cases of no current job, the current job class will be NULL and the user can add multiple job offers to compare.

Jobs can have different locations. Each job has its own ranking and rating which would be passed to the job Ranker Class.