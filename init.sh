export SPRING_DATASOURCE_USERNAME=$(cat /secrets/oracle/username)
export SPRING_DATASOURCE_PASSWORD=$(cat /secrets/oracle/password)

echo $SPRING_DATASOURCE_URL
echo $SPRING_DATASOURCE_USERNAME
echo $SPRING_DATASOURCE_PASSWORD

if [ $(echo $SPRING_DATASOURCE_URL | grep -i '|') ]; then
    PRIMARY=$(echo "${SPRING_DATASOURCE_URL}" | cut -d '|' -f 1)
    FAILOVER=$(echo "${SPRING_DATASOURCE_URL}" | cut -d '|' -f 2)

    SERVICE_NAME=$(echo "${PRIMARY}" | cut -d '/' -f 2)
    PRIMARY_HOST=$(echo "${PRIMARY}" | cut -d '@' -f 2 | cut -d '/' -f 1 | cut -d ':' -f 1)
    PRIMARY_PORT=$(echo "${PRIMARY}" | cut -d '@' -f 2 | cut -d '/' -f 1 | cut -d ':' -f 2)
    FAILOVER_HOST=$(echo "${FAILOVER}" | cut -d '@' -f 2 | cut -d '/' -f 1 | cut -d ':' -f 1)
    FAILOVER_PORT=$(echo "${FAILOVER}" | cut -d '@' -f 2 | cut -d '/' -f 1 | cut -d ':' -f 2)

    export SPRING_DATASOURCE_URL="jdbc:oracle:thin:@(DESCRIPTION=(FAILOVER=on)(CONNECT_TIMEOUT=15)(RETRY_COUNT=20)(RETRY_DELAY=3)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=$PRIMARY_HOST)(PORT=$PRIMARY_PORT)))(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=$FAILOVER_HOST)(PORT=$FAILOVER_PORT)))(CONNECT_DATA=(SERVICE_NAME=$SERVICE_NAME)))"
fi