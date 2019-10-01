#!/bin/bash
export BUILD=$1
export LAST_TAG=$(git for-each-ref --sort=taggerdate --format '%(refname) %(taggerdate)' refs/tags|tail -n1|cut -d'/' -f3|cut -d' ' -f1)

function commits {
    git log $LAST_TAG..HEAD | grep "$1:" | cut -d':' -f2 | sed -e 's/^ */• /'
}

export FIXES=$(commits fix)
export FEATURES=$(commits feat)

echo "Version $BUILD of Secret App contains the following changes:"
echo 

if [ -n "$FEATURES" ]
then
    echo 'Features:'
    echo
    commits feat
    echo
fi

if [ -n "$FIXES" ]
then
    echo 'Fixes:'
    echo
    commits fix
fi

