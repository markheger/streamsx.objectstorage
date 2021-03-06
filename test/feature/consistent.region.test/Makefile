# Copyright (C) 2017, International Business Machines Corporation. 
# All Rights Reserved.

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
  # Set the STREAMSX_OBJECTSTORAGE_TOOLKIT variable if streamsx.objectstorage sample or toolkit location changed in your environment.
  ifeq ($(STREAMSX_OBJECTSTORAGE_TOOLKIT),)
    ifneq ($(realpath ../../../com.ibm.streamsx.objectstorage),)
      STREAMSX_OBJECTSTORAGE_TOOLKIT = ../../../com.ibm.streamsx.objectstorage
    endif
    ifeq ($(STREAMSX_OBJECTSTORAGE_TOOLKIT),)
       ifneq ($(realpath $(STREAMS_INSTALL)/toolkits/com.ibm.streamsx.objectstorage),)
          STREAMSX_OBJECTSTORAGE_TOOLKIT = $(STREAMS_INSTALL)/toolkits/com.ibm.streamsx.objectstorage
       endif
    endif
  endif
  SPLC = $(STREAMS_INSTALL)/bin/sc
  SPL_PATH = $(STREAMSX_OBJECTSTORAGE_TOOLKIT)
endif

SPLC_FLAGS += -t $(SPL_PATH) --rebuild-toolkits

SPL_CMD_ARGS ?= 

build: distributed 

all: clean build


distributed: ObjectStorageScan_consistent_region ObjectStorageSource_consistent_region_static_name ObjectStorageSource_consistent_region_static_name_binary S3ObjectStorageSource_consistent_region_static_name_binary S3ObjectStorageSource_consistent_region_static_name ObjectStorageSink_consistent_region ObjectStorageSink_consistent_region_parquet


ObjectStorageScan_consistent_region:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::ObjectStorageScan_consistent_region --output-directory=$(OUTPUT_DIR)/ObjectStorageScan_consistent_region $(SPL_CMD_ARGS)


ObjectStorageSource_consistent_region_static_name:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::ObjectStorageSource_consistent_region_static_name --output-directory=$(OUTPUT_DIR)/ObjectStorageSource_consistent_region_static_name $(SPL_CMD_ARGS)

ObjectStorageSource_consistent_region_static_name_binary:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::ObjectStorageSource_consistent_region_static_name_binary --output-directory=$(OUTPUT_DIR)/ObjectStorageSource_consistent_region_static_name_binary $(SPL_CMD_ARGS)

S3ObjectStorageSource_consistent_region_static_name:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::S3ObjectStorageSource_consistent_region_static_name --output-directory=$(OUTPUT_DIR)/S3ObjectStorageSource_consistent_region_static_name $(SPL_CMD_ARGS)

S3ObjectStorageSource_consistent_region_static_name_binary:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::S3ObjectStorageSource_consistent_region_static_name_binary --output-directory=$(OUTPUT_DIR)/S3ObjectStorageSource_consistent_region_static_name_binary $(SPL_CMD_ARGS)

ObjectStorageSink_consistent_region:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::ObjectStorageSink_consistent_region --output-directory=$(OUTPUT_DIR)/ObjectStorageSink_consistent_region $(SPL_CMD_ARGS)

ObjectStorageSink_consistent_region_parquet:
	$(SPLC) $(SPLC_FLAGS) -M com.ibm.streamsx.objectstorage.test::ObjectStorageSink_consistent_region_parquet --output-directory=$(OUTPUT_DIR)/ObjectStorageSink_consistent_region_parquet $(SPL_CMD_ARGS)


clean:
	rm -f toolkit.xml
	rm -rf $(OUTPUT_DIR)

tkidx:
	$(STREAMS_INSTALL)/bin/spl-make-toolkit -i .

