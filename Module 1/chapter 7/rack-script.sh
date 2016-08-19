#!/bin/bash

if [ $1 = "10.0.0.101" ]; then
    echo -n "/rack1 "
else
    echo -n "/default-rack "
fi
