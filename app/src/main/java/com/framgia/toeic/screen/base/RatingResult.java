package com.framgia.toeic.screen.base;

import android.support.annotation.IntDef;

import static com.framgia.toeic.screen.base.RatingResult.GOOD;
import static com.framgia.toeic.screen.base.RatingResult.NORMAL;
import static com.framgia.toeic.screen.base.RatingResult.VERY_GOOD;
import static com.framgia.toeic.screen.base.RatingResult.BAD;
import static com.framgia.toeic.screen.base.RatingResult.VERY_BAD;

@IntDef({VERY_GOOD, GOOD, NORMAL, BAD, VERY_BAD})
public @interface RatingResult {
    int VERY_GOOD = 5;
    int GOOD = 4;
    int NORMAL = 3;
    int BAD = 2;
    int VERY_BAD = 1;
}
