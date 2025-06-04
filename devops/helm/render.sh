#!/bin/bash
source ./config.sh
source ./init.sh

if [ "$HELM_VERSION" == "v2" ]; then 
    NAME_OPT="--name"
fi

helm template $NAME_OPT $DEPLOYMENT \
    --namespace $NAMESPACE \
    -f ${VALUES}/values.yaml \
    ./$CHART
