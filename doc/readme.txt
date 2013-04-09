从github上直接取下来，在高版本的eclipse中运行不了，报错如下：
java.lang.RuntimeException: Application "org.eclipse.ui.ide.workbench" could not be found in the registry. The applications available are: org.eclipse.jdt.core.JavaCodeFormatter, org.eclipse.update.core.standaloneUpdate, org.eclipse.update.core.siteOptimizer, org.eclipse.equinox.app.error.

遇到这种问题，通过以下方式进行解决：
run configurations中添加插件依赖：
1.org.eclipse.ui.ide.application
2.org.apache.commons.logging