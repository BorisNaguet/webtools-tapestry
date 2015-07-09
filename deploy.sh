#!/bin/bash
set -e # exit with nonzero exit code if anything fails

# clear and re-create the out directory
rm -rf p2Repo || exit 0;

pwd
ls
ls webtools-tapestry-repository
ls webtools-tapestry-repository/target
ls webtools-tapestry-repository/target/repository

#copy content
cp -a webtools-tapestry-repository/target/repository/. p2Repo/

# go to the p2Repo directory and create a *new* Git repo
cd p2Repo
ls
git init

# inside this git repo we'll pretend to be a new user
git config user.name "Travis CI"
git config user.email "boris.naguet@atos.net "

# The first and only commit to this new Git repo contains all the
# files present with the commit message "Deploy to GitHub Pages".
git add .
git commit -m "Deploy to GitHub Pages"

echo "commit done"

# Force push from the current repo's master branch to the remote
# repo's gh-pages branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
git push --force "https://${GH_TOKEN}@${GH_REF}" master:gh-pages

echo "push done"