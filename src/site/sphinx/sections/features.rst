********
Features
********

.. _processing::

Processing
==========

After fetching a message,

Processors Example
------------------------------------

.. code-block:: yaml

  com:
    octotelematics:
      otp:
        processor:
          processors:
            - name: InlineScript
              signature: event
              code: |-
                from otp import *
                event = model.Event().withType( "TEST" )
                event.context("NEW-CONTEXT-FIELD").setString("NEW-CONTEXT-VALUE")
                event.addTags("TEST")
                return [ Input, event ]

            - name: RemoteScript
              signature: event
              image: 'obd:processing:test-image-id'



.. _routing::

Routing
=======

The ``Message Preprocessor`` implements a routing configuration based on **event type** and **tags** matching.

The routing can act as a filter for the inbound messages or as a routing configuration for the outbound messages.

An instance of the ``Message Preprocessor`` can read messages from up to two different ``Message`` sources and a ``Packet`` source, and can write messages up to three different ``Message`` destinations and a single ``Packet`` destination.

``Packet`` and ``Message`` are two data representation of a message.
The ``Packet`` data structure is a legacy data structure deeply tied to the CLEX protocol and that has been kept for compatibility reason with some components of the OTP platform.
The ``Message`` data structure is a protocol-agnostic representation that has become the standard message representation used inside the OTP platform.

Although they can contain the same information, these data structure are not interchangeable, so a conversion is needed so that a message from a source with a specific representation may be redirected to a destination with a different representation.

The **selector** is the structure that defines a filter criteria (if used for inbound messages) or a routing criteria (if used for outbound messages).

A selector has a **name** that acts as an identifier for the selector and a list of **routes** that contains the route's criteria.

A route has a **name**, a **destination**, a list of **events** and a list of **rules**.
If it is used for filtering, the **destination** specifies a keyword that can be used inside the business logic (eg. to determine the type of processing a message has to do) while, if it is used for routing, the **destination** determines the destination topic of the outbound message.

A message will match a route if all these conditions are true:

* the type of the message will match at least one of the route **event** entries
* the message context will match at least one of the route **rules**

.. info::

    If expressed in a KEY:VALUE form, a **rule** will match a context value of the message otherwise. if no ``:`` char is found, the rule will match a tag of the message.

Since **events** and **rules** are optional, a route can have no events and rules. A such configured route will not match anything and will be considered as an all-blocking filter.

It is possible to configure a **default** destination for a selector. It will be used in case no route will match the message.


Configuration Example (YAML)
----------------------------

.. code-block:: yaml

  com:
    octotelematics:
      otp:

        processor:
          processors:
            - name: InlineScript
              signature: event
              code: |-
                from otp import *
                event = model.Event().withType( "TEST" )
                event.context("NEW-CONTEXT-FIELD").setString("NEW-CONTEXT-VALUE")
                event.addTags("TEST")
                return [ Input, event ]

            - name: RemoteScript
              signature: event
              image: 'obd:processing:test-image-id'

        routing:
          selectors:
            - name: message-source-1-filter
              default: pass
              routes:
                - name: inline-script-example
                  destination: pass
                  features:
                    - process/InlineScript
                  events:
                    - EVT:TRIP
                - name: remote-script-example
                  destination: pass
                  features:
                    - process/RemoteScript
                  events:
                    - EVT:CRASH
            - name: message-output-1-selector
              routes:
                - name: clex-out
                  destination: message.raw.clex.platform
                  events:
                   - CLEX
                  rules:
                   - GW/DAT
                   - GW/FEND
                - name: gpro-out
                  destination: message.raw.gpro.platform
                  rules:
                   - GPRO
