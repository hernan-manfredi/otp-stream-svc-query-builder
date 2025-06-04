#!/bin/bash
source ./config.sh
source ./init.sh

if [ "$HELM_VERSION" == "v2" ]; then 
    $HELM del --purge $DEPLOYMENT
else
    $HELM uninstall $DEPLOYMENT --namespace=${NAMESPACE}
fi
