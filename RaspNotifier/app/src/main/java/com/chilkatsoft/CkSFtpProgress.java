/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.chilkatsoft;

public class CkSFtpProgress extends CkBaseProgress {
  private transient long swigCPtr;

  protected CkSFtpProgress(long cPtr, boolean cMemoryOwn) {
    super(chilkatJNI.CkSFtpProgress_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CkSFtpProgress obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        chilkatJNI.delete_CkSFtpProgress(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    chilkatJNI.CkSFtpProgress_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    chilkatJNI.CkSFtpProgress_change_ownership(this, swigCPtr, true);
  }

  public CkSFtpProgress() {
    this(chilkatJNI.new_CkSFtpProgress(), true);
    chilkatJNI.CkSFtpProgress_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

  public void UploadRate(long byteCount, long bytesPerSec) {
    if (getClass() == CkSFtpProgress.class) chilkatJNI.CkSFtpProgress_UploadRate(swigCPtr, this, byteCount, bytesPerSec); else chilkatJNI.CkSFtpProgress_UploadRateSwigExplicitCkSFtpProgress(swigCPtr, this, byteCount, bytesPerSec);
  }

  public void DownloadRate(long byteCount, long bytesPerSec) {
    if (getClass() == CkSFtpProgress.class) chilkatJNI.CkSFtpProgress_DownloadRate(swigCPtr, this, byteCount, bytesPerSec); else chilkatJNI.CkSFtpProgress_DownloadRateSwigExplicitCkSFtpProgress(swigCPtr, this, byteCount, bytesPerSec);
  }

}
