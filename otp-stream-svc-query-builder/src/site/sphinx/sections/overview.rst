********
Overview
********

The ``Universal Processor`` makes use of an embedded python engine to execute arbitrary logic.

It can fetch messages from several sources and publish the result of the execution using a rule-based routing configuration.


Scripting
=========

The ``Universal Processor`` embeds a python scripting engine based on Jython with the addition of the OCTO data model library.

A script can be provided with the component installation or can be loaded as ``image`` from the ``Image Manager`` component.
In the former case, the script cannot be changed at runtime and it is required to reinstall the ``Universal Processor`` instance with the new script; the latter instead doesn't require any reinstall since the script is fetched anew at each execution.

Since the script engine is based on Jython (which is an old implementation of the Python interpreter), the Python language functionalities are limited to the version 2.7 of the Python language specification.
