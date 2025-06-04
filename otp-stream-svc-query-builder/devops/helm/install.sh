#!/bin/bash
source ./config.sh
source ./init.sh

$HELM upgrade -i $DEPLOYMENT \
    --namespace $NAMESPACE \
    -f ${VALUES}/values.yaml \
    ./$CHART
