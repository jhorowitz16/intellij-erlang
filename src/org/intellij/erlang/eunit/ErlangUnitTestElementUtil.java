/*
 * Copyright 2012-2014 Sergey Ignatov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.intellij.erlang.eunit;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import org.intellij.erlang.psi.ErlangFile;
import org.intellij.erlang.psi.ErlangFunction;
import org.intellij.erlang.psi.impl.ErlangPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ErlangUnitTestElementUtil {
  private ErlangUnitTestElementUtil() {
  }

  @NotNull
  public static Collection<ErlangFunction> findFunctionTestElements(@Nullable PsiElement element) {
    if (element != null) {
      PsiFile containingFile = element.getContainingFile();
      if (getFileTestElement(containingFile) == null) {
        return ContainerUtil.emptyList();
      }
    }
    return ContainerUtil.createMaybeSingletonList(getZeroArityFunction(element));
  }

  public static Collection<ErlangFile> findFileTestElements(Project project, DataContext dataContext) {
    VirtualFile[] selectedFiles = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(dataContext);

    if (selectedFiles == null) return Collections.emptyList();

    List<ErlangFile> testFiles = new ArrayList<>(selectedFiles.length);
    PsiManager psiManager = PsiManager.getInstance(project);
    for (VirtualFile file : selectedFiles) {
      if (!file.isDirectory()) {
        ContainerUtil.addIfNotNull(testFiles, getFileTestElement(psiManager.findFile(file)));
        continue;
      }

      PsiDirectory directory = psiManager.findDirectory(file);
      PsiFile[] children = directory == null ? new PsiFile[0] : directory.getFiles();
      for (PsiFile psiFile : children) {
        ContainerUtil.addIfNotNull(testFiles, getFileTestElement(psiFile));
      }
    }
    return testFiles;
  }

  @Nullable
  private static ErlangFunction getZeroArityFunction(@Nullable PsiElement psiElement) {
    ErlangFunction function = psiElement instanceof ErlangFunction ? (ErlangFunction)psiElement : PsiTreeUtil.getParentOfType(psiElement, ErlangFunction.class);
    int arity = function == null ? -1 : function.getArity();
    return 0 == arity ? function : null;
  }

  @Nullable
  private static ErlangFile getFileTestElement(@Nullable PsiFile psiFile) {
    return psiFile instanceof ErlangFile && ErlangPsiImplUtil.isEunitImported((ErlangFile) psiFile) ? (ErlangFile) psiFile : null;
  }
}
