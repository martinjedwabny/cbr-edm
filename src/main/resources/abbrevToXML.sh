#!/bin/bash

riot --output=RDFXML $1 > $2; sed -i '' '/NamedIndividual/d' $2