************
Architecture
************

High Level
==========

.. uml::

    @startuml
    !theme mars
    left to right direction

    frame "Packet Source" {
      queue "avro.raw.<protocol>.<qualifier>" as QinPkt1
      queue "<i> other configured topics </i>" as QinPktOther
    }

    frame "Message Source 1" {
      queue "message.raw.<protocol>.<qualifier>" as QinMsg1
      queue "<i> other configured topics </i>" as QinMsgOther1
    }

    frame "Message Source 2" {
      queue "message.raw.<protocol>.<qualifier>" as QinMsg3
      queue "<i> other configured topics </i>" as QinMsgOther2
    }

    component "Message Preprocessor" as pre
    component "Configuration Central" as cc

    frame "Message Direction 1" {
      queue "message.raw.<protocol>.<qualifier>" as QoutMsg1
      queue "<i> other configured topics </i>" as QoutMsgOther1
    }

    frame "Message Direction 2" {
      queue "message.raw.<protocol>.<qualifier>" as QoutMsg3
      queue "<i> other configured topics </i>" as QoutMsgOther2
    }

    frame "Message Direction 3" {
      queue "message.raw.<protocol>.<qualifier>" as QoutMsg5
      queue "<i> other configured topics </i>" as QoutMsgOther3
    }

    frame "Compatibility Direction" {
      queue "avro.raw.clex.platform" as QoutPkt
    }

    QinPkt1 ----> pre
    QinPktOther ....> pre
    QinMsg1 ----> pre
    QinMsgOther1 ....> pre
    QinMsg3 ----> pre
    QinMsgOther2 ....> pre

    pre -r-# cc : HTTP

    pre ----> QoutMsg1
    pre ....> QoutMsgOther1
    pre ----> QoutMsg3
    pre ....> QoutMsgOther2
    pre ----> QoutMsg5
    pre ....> QoutMsgOther3
    pre ----> QoutPkt

    @enduml

Each **source** and **direction** is a configurations with a specific Kafka cluster and a specific encoding.

There can be up to two ``Message`` sources and one ``Packet`` source, while there can be up to three ``Message`` destinations and a single ``Packet`` destination.

While a source can consume messages from multiple topics (from the same Kafka cluster), a destination can publish a message only on a single topic.

It is however possible to manipulate the final outbound topic by using a routing configuration (see :ref:`routing`).

Installables
============

Having a single instance of the ``Message Preprocessor`` can generate an intricated configuration, leading to unexpected or unwanted routing.

To avoid this, it is possible to deploy different instances of the ``Message Preprocessor``, each with its unique configuration.

These instances are called **installables**.

CLEX-EVM Installable
----------------

.. uml::

    @startuml
    !theme mars
    left to right direction

    component "Message Preprocessor\n<<CLEX-EVM Installable>>" as mp
    component "Configuration Central" as cc

    frame "Message Source" {
        queue message.raw.clex as Qfend #green
    }

    frame "Message Direction" {
        queue message.raw.clex.platform as Qrawplat
        queue message.raw.clex.discard as Qrawdisc
        queue message.raw.clex.unknown as Qrawunk
        queue message.raw.clex.privacy as Qrawpriv
        queue message.raw.clex.park as Qrawpark
        queue message.raw.clex.platform.connection as Qrawconn
    }

    Qfend --> mp

    mp -l-# cc : HTTP

    mp --> Qrawplat
    mp --> Qrawdisc
    mp --> Qrawunk
    mp --> Qrawpriv
    mp --> Qrawpark
    mp --> Qrawconn

    @enduml

CLEX-NGP Installable
----------------

.. uml::

    @startuml
    !theme mars
    left to right direction

    component "Message Preprocessor\n<<CLEX-NGP Installable>>" as mp
    component "Configuration Central" as cc

    frame "Packet Source" {
        queue avro.raw.clex.dat as Qdat
    }

    frame "Message Source" {
        queue message.raw.clex as Qfend
    }

    frame "Message Direction" {
        queue message.raw.clex.platform as Qrawplat
        queue message.raw.clex.discard as Qrawdisc
        queue message.raw.clex.unknown as Qrawunk
        queue message.raw.clex.privacy as Qrawpriv
        queue message.raw.clex.park as Qrawpark
    }

    Qdat --> mp
    Qfend --> mp

    mp -l-# cc : HTTP

    mp --> Qrawplat
    mp --> Qrawdisc
    mp --> Qrawplat
    mp --> Qrawunk
    mp --> Qrawpriv
    mp --> Qrawpark

    @enduml


CLEX-PACKET Installable
----------------

.. uml::

    @startuml
    !theme mars
    left to right direction

    component "Message Preprocessor\n<<CLEX-PACKET Installable>>" as mp
    component "Configuration Central" as cc

    frame "Packet Source" {
        queue avro.raw.clex.dat as Qdat
        queue avro.raw.clex.fend as Qfendpacket
    }

    frame "Message Source" {
        queue message.raw.clex as Qfend #green
    }

    frame "Message Direction" {
        queue message.raw.clex.platform as Qrawplat
    }

    frame "Compatibility Direction" {
        queue avro.raw.clex.platform as Qlegacy
    }

    Qdat --> mp
    Qfend --> mp
    Qfendpacket --> mp

    mp -l-# cc : HTTP

    mp --> Qrawplat
    mp --> Qlegacy

    @enduml


GPRO Installable
----------------

.. uml::

    @startuml
    !theme mars
    left to right direction

    frame "Message Source" {
      queue message.raw.gpro as Qgpro
    }

    component "Configuration Central" as cc

    frame "Message Direction" {
      queue message.raw.gpro.platform as Qgprorich
    }

    component "Message Preprocessor\n<<GPRO Installable>>" as pp

    Qgpro --> pp : GPRO:RAW
    pp -r-# cc : HTTP
    pp --> Qgprorich : GPRO:RAW

    @enduml


DANLAW Installable
----------------

.. uml::

    @startuml
    !theme mars
    left to right direction

    frame "Message Source" {
      queue message.raw.danlaw as Qin
    }

    component "Configuration Central" as cc

    frame "Message Direction" {
      queue message.raw.danlaw.platform as Qout
    }

    component "Message Preprocessor\n<<DANLAW Installable>>" as pp

    Qin --> pp : DANLAW:RAW
    pp -r-# cc : HTTP
    pp --> Qout

    @enduml


VNEXT Installable
----------------

.. uml::

    @startuml
    !theme mars
    left to right direction

    frame "Message Source" {
      queue message.raw.vnext.evo as Qevo
      queue message.raw.vnext as Qvnext
    }

    component "Configuration Central" as cc

    frame "Message Direction" {
      queue message.raw.vnext.platform as Qout
    }

    component "Message Preprocessor\n<<VNEXT Installable>>" as pp

    Qevo --> pp : EVT:PCK, CSM:65163090
    Qvnext --> pp : EVT:PCK
    pp -r-# cc : HTTP
    pp --> Qout

    @enduml
