LOCAL_PATH := $(call my-dir)
ROOT_PATH := $(LOCAL_PATH)
include $(CLEAR_VARS)

LOCAL_MODULE := video
#add src header
LOCAL_SRC_FILES := com_duoyi_smallvideolib_YuvHelper.c
LOCAL_STATIC_LIBRARIES := libyuv
include $(BUILD_SHARED_LIBRARY)


include $(ROOT_PATH)/libyuv/Android.mk