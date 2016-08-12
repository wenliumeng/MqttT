package com.cn.seymour.mqttt.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LLinearLayoutView
  extends LinearLayout
{
  private Context mContext = null;
  private Drawable mBackground = null;
  private StateListDrawable statelistDrawable = null;
  
  public LLinearLayoutView(Context context)
  {
    super(context);
    this.mContext = context;
  }
  
  public LLinearLayoutView(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    this.mContext = context;
  }
  
  public void setBackgroundImg(int normalImgId, int pressedImgId, int foucesdImgId, int selectedImgId, int enabledImgId)
  {
    if (this.statelistDrawable == null)
    {
      this.statelistDrawable = new StateListDrawable();
      setBackgroundDrawable(null);
      this.mBackground = this.statelistDrawable;
      
      this.mBackground.setCallback(this);
    }
    Drawable normal = normalImgId > 0 ? this.mContext.getResources().getDrawable(normalImgId) : null;
    Drawable pressed = pressedImgId > 0 ? this.mContext.getResources().getDrawable(pressedImgId) : null;
    Drawable focus = foucesdImgId > 0 ? this.mContext.getResources().getDrawable(foucesdImgId) : null;
    Drawable enabled = enabledImgId > 0 ? this.mContext.getResources().getDrawable(enabledImgId) : null;
    Drawable selected = selectedImgId > 0 ? this.mContext.getResources().getDrawable(selectedImgId) : null;
    if (focus != null) {
      this.statelistDrawable.addState(new int[] { 16842910, 16842908 }, focus);
    }
    if (pressed != null) {
      this.statelistDrawable.addState(new int[] { 16842919, 16842910 }, pressed);
    }
    if (focus != null) {
      this.statelistDrawable.addState(new int[] { 16842908 }, focus);
    }
    if (pressed != null) {
      this.statelistDrawable.addState(new int[] { 16842919 }, pressed);
    }
    if (selected != null) {
      this.statelistDrawable.addState(new int[] { 16842919 }, selected);
    }
    if (enabled != null) {
      this.statelistDrawable.addState(new int[] { -16842910 }, enabled);
    }
    if (normal != null) {
      this.statelistDrawable.addState(new int[0], normal);
    }
  }
  
  public void setBackgroundColor(int normalColor, int pressedColor, int foucesdColor, int enabledColor, int selectedColor, float roundRadius, int stokeWidth, int stokeColor)
  {
    if (this.statelistDrawable == null)
    {
      this.statelistDrawable = new StateListDrawable();
      setBackgroundDrawable(null);
      this.mBackground = this.statelistDrawable;
      
      this.mBackground.setCallback(this);
    }
    GradientDrawable gdNormal = null;
    if (normalColor != -10)
    {
      gdNormal = new GradientDrawable();
      gdNormal.setColor(normalColor);
      gdNormal.setCornerRadius(roundRadius);
      gdNormal.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdPressed = null;
    if (pressedColor != -10)
    {
      gdPressed = new GradientDrawable();
      gdPressed.setColor(pressedColor);
      gdPressed.setCornerRadius(roundRadius);
      gdPressed.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdFocus = null;
    if (foucesdColor != -10)
    {
      gdFocus = new GradientDrawable();
      gdFocus.setColor(foucesdColor);
      gdFocus.setCornerRadius(roundRadius);
      gdFocus.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdEnabled = null;
    if (enabledColor != -10)
    {
      gdEnabled = new GradientDrawable();
      gdEnabled.setColor(enabledColor);
      gdEnabled.setCornerRadius(roundRadius);
      gdEnabled.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdSelected = null;
    if (selectedColor != -10)
    {
      gdSelected = new GradientDrawable();
      gdSelected.setColor(selectedColor);
      gdSelected.setCornerRadius(roundRadius);
      gdSelected.setStroke(stokeWidth, stokeColor);
    }
    Drawable normal = gdNormal;
    Drawable pressed = gdPressed;
    Drawable focus = gdFocus;
    Drawable enabled = gdEnabled;
    Drawable selected = gdSelected;
    if (focus != null) {
      this.statelistDrawable.addState(new int[] { 16842910, 16842908 }, focus);
    }
    if (pressed != null) {
      this.statelistDrawable.addState(new int[] { 16842919, 16842910 }, pressed);
    }
    if (focus != null) {
      this.statelistDrawable.addState(new int[] { 16842908 }, focus);
    }
    if (pressed != null) {
      this.statelistDrawable.addState(new int[] { 16842919 }, pressed);
    }
    if (selected != null) {
      this.statelistDrawable.addState(new int[] { 16842919 }, selected);
    }
    if (enabled != null) {
      this.statelistDrawable.addState(new int[] { -16842910 }, enabled);
    }
    if (normal != null) {
      this.statelistDrawable.addState(new int[0], normal);
    }
  }
  
  public void setBackgroundColor2(int normalColor, int pressedColor, int foucesdColor, int enabledColor, int selectedColor, float[] roundRadius, int stokeWidth, int stokeColor)
  {
    if (this.statelistDrawable == null)
    {
      this.statelistDrawable = new StateListDrawable();
      setBackgroundDrawable(null);
      this.mBackground = this.statelistDrawable;
      
      this.mBackground.setCallback(this);
    }
    GradientDrawable gdNormal = null;
    if (normalColor != -10)
    {
      gdNormal = new GradientDrawable();
      gdNormal.setColor(normalColor);
      gdNormal.setCornerRadii(roundRadius);
      gdNormal.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdPressed = null;
    if (pressedColor != -10)
    {
      gdPressed = new GradientDrawable();
      gdPressed.setColor(pressedColor);
      gdPressed.setCornerRadii(roundRadius);
      gdPressed.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdFocus = null;
    if (foucesdColor != -10)
    {
      gdFocus = new GradientDrawable();
      gdFocus.setColor(foucesdColor);
      gdFocus.setCornerRadii(roundRadius);
      gdFocus.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdEnabled = null;
    if (enabledColor != -10)
    {
      gdEnabled = new GradientDrawable();
      gdEnabled.setColor(enabledColor);
      gdEnabled.setCornerRadii(roundRadius);
      gdEnabled.setStroke(stokeWidth, stokeColor);
    }
    GradientDrawable gdSelected = null;
    if (selectedColor != -10)
    {
      gdSelected = new GradientDrawable();
      gdSelected.setColor(selectedColor);
      gdSelected.setCornerRadii(roundRadius);
      gdSelected.setStroke(stokeWidth, stokeColor);
    }
    Drawable normal = gdNormal;
    Drawable pressed = gdPressed;
    Drawable focus = gdFocus;
    Drawable enabled = gdEnabled;
    Drawable selected = gdSelected;
    if (focus != null) {
      this.statelistDrawable.addState(new int[] { 16842910, 16842908 }, focus);
    }
    if (pressed != null) {
      this.statelistDrawable.addState(new int[] { 16842919, 16842910 }, pressed);
    }
    if (focus != null) {
      this.statelistDrawable.addState(new int[] { 16842908 }, focus);
    }
    if (pressed != null) {
      this.statelistDrawable.addState(new int[] { 16842919 }, pressed);
    }
    if (selected != null) {
      this.statelistDrawable.addState(new int[] { 16842919 }, selected);
    }
    if (enabled != null) {
      this.statelistDrawable.addState(new int[] { -16842910 }, enabled);
    }
    if (normal != null) {
      this.statelistDrawable.addState(new int[0], normal);
    }
  }
  
  protected void drawableStateChanged()
  {
    Drawable d = this.mBackground;
    if ((d != null) && (d.isStateful())) {
      d.setState(getDrawableState());
    }
    super.drawableStateChanged();
    setBackground(d);
  }
  
  protected boolean verifyDrawable(Drawable who)
  {
    return (who == this.mBackground) || (super.verifyDrawable(who));
  }
  
  public void refreshDrawableState()
  {
    super.refreshDrawableState();
    drawableStateChanged();
  }
}
