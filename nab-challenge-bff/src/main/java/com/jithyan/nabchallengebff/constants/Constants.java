package com.jithyan.nabchallengebff.constants;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class Constants {
   public static final ZoneOffset CURRENT_ZONE_ID = ZoneId
         .of(java.time.ZoneId.systemDefault().getId())
         .getRules()
         .getOffset(Instant.now());

   private Constants() {}
}
