# Licensed Materials - Property of IBM
# ObjectStorage Toolkit for IBM Streams
# (c) Copyright IBM Corp. 2018
# All rights reserved.

.PHONY: build all clean distributed

SPLC_FLAGS = -a
OUTPUT_DIR = output
ifeq ($(STREAMS_STUDIO_BUILDING), 1)
  # Building from Streams Studio, use env vars set by studio
  SPLC = $(STREAMS_STUDIO_SC_PATH)
  SPL_PATH = $(STREAMS_STUDIO_SPL_PATH)
  OUTPUT_DIR = $(STREAMS_STUDIO_OUTPUT_DIRECTORY)
else
  # Building outside of Streams Studio...
  ifeq ($(STREAMS_INSTALL),)
    $(error error: environment variable STREAMS_INSTALL has to be set)
  endif
  # Set this variable if streamsx.objectstorage sample or toolkit location changed in your environment.
  STREAMSX_OBJECTSTORAGE_TOOLKIT ?=../../com.ibm.streamsx.objectstorage
  ifeq ($(STREAMSX_AVRO_TOOLKIT),)
    ifneq ($(realpath ../../../streamsx.avro/com.ibm.streamsx.avro),)
      STREAMSX_AVRO_TOOLKIT=../../../streamsx.avro/com.ibm.streamsx.avro
    endif
  endif
  STREAMSX_JSON_TOOLKIT ?=$(STREAMS_INSTALL)/toolkits/com.ibm.streamsx.json
  SPLC = $(STREAMS_INSTALL)/bin/sc
  ADDITIONAL_TOOLKITS =
  ifneq ($(STREAMSX_AVRO_TOOLKIT),)
    ADDITIONAL_TOOLKITS:=:$(STREAMSX_AVRO_TOOLKIT):$(STREAMSX_JSON_TOOLKIT)
  endif
  SPL_PATH=$(STREAMSX_OBJECTSTORAGE_TOOLKIT)$(ADDITIONAL_TOOLKITS)
endif

SPLC_FLAGS += -t $(SPL_PATH) --rebuild-toolkits
SPL_CMD_ARGS ?= 

SPL_MAIN_COMPOSITE = com.ibm.streamsx.objectstorage.formats.demo::FormatsDemo

build: distributed 

all: clean build

distributed:
	$(SPLC) $(SPLC_FLAGS) -M $(SPL_MAIN_COMPOSITE) $(SPL_CMD_ARGS) --output-directory=$(OUTPUT_DIR)

clean:
	$(SPLC) $(SPLC_FLAGS) -C -M $(SPL_MAIN_COMPOSITE)
	rm -rf $(OUTPUT_DIR)
	rm -rf doc

tkidx:
	$(STREAMS_INSTALL)/bin/spl-make-toolkit -i .

