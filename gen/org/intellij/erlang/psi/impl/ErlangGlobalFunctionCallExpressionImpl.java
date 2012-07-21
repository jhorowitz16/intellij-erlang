// This is a generated file. Not intended for manual editing.
package org.intellij.erlang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.intellij.erlang.ErlangTypes.*;
import org.intellij.erlang.psi.*;

public class ErlangGlobalFunctionCallExpressionImpl extends ErlangExpressionImpl implements ErlangGlobalFunctionCallExpression {

  public ErlangGlobalFunctionCallExpressionImpl(ASTNode node) {
    super(node);
  }

  @Override
  @NotNull
  public ErlangExpression getExpression() {
    return findNotNullChildByClass(ErlangExpression.class);
  }

  @Override
  @NotNull
  public ErlangModuleRef getModuleRef() {
    return findNotNullChildByClass(ErlangModuleRef.class);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ErlangVisitor) ((ErlangVisitor)visitor).visitGlobalFunctionCallExpression(this);
    else super.accept(visitor);
  }

}