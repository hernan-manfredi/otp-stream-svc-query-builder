#/bin/bash
# Helm init script 1.0
# 
ENV=undefined
HELM=helm

while getopts e:i:v:n:s:t:h flag
do
    case "${flag}" in
        e) ENV=${OPTARG};;
        i) INSTALLATION=${OPTARG};;
        v) VALUES=${OPTARG};;
        n) NAME=${OPTARG};;
        s) SPACE=${OPTARG};;
        t) TAG=${OPTARG};;
        h) HELP=yes;;
    esac
done

if [ -z ${INSTALLATION+x} ]; then
  INSTALLABLE=${ENV}
else
  INSTALLABLE=${ENV}-${INSTALLATION}
fi

DEPLOYMENT=${NAME}-${INSTALLABLE}
NAMESPACE=eu-i1-${ENV}-${SPACE}

if [ -z ${VALUES+x} ]; then
    VALUES=./values/${INSTALLABLE}
fi

echo "Environment   : $ENV"
echo "Installable   : $INSTALLABLE"
echo "Values folder : $VALUES"
echo "Deployment    : $DEPLOYMENT"
echo "Namespace     : $NAMESPACE"
#echo "Tag           : $TAG"

if [ "$ENV" == "undefined" ] || [ "$HELP" == "yes" ]; then
	echo Usage: $0 -e \{sit\|uat\} [ -i \<installation\> ] [ -v \<values folder\> ] [ -n \<deployment name\> ] [ -s \<namespace\> ] [ -2 \(helm2\) ] [ -h ]
    exit 0
fi
