#======================================================================
#
# example 'make package SKIP_TEST=true'
#
# author: mingsha
# date: 2025-07-10
#======================================================================

SHELL := /bin/bash -o pipefail

export BASE_PATH := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# ----------------------------- variables <-----------------------------
# unit test flag
SKIP_TEST ?= false
# pom version
NEW_VERSION ?= 2025.07.10.01
# ----------------------------- variables >-----------------------------

# ----------------------------- help <-----------------------------
.PHONY: help
help:
	@echo "\033[1;35m🚀 Mingsha Kernel 项目命令速查表\033[0m"
	@echo "\033[1;33m========================================\033[0m"
	@echo "\033[1;36m可用命令：\033[0m"
	@grep -E '^[a-zA-Z_-]+:.*?##' $(MAKEFILE_LIST) | \
		sed 's/clean:/🧹 clean:/; s/test:/🧪 test:/; s/package:/📦 package:/; s/install:/📥 install:/; s/deploy:/🚀 deploy:/; s/set-version:/🏷️ set-version:/; s/help:/❓ help:/' | \
		awk -F: '{printf "  \033[1;32m%-18s\033[0m %s\n", $$1, substr($$2, index($$2,$$3))}'
	@echo "\033[1;33m========================================\033[0m"
	@echo "\033[1;34m✨ 用法示例：make package SKIP_TEST=true NEW_VERSION=2025.07.10.01\033[0m"

# ----------------------------- maven <-----------------------------

clean:        ## 清理所有模块的构建产物
	$(BASE_PATH)/mvnw --batch-mode --errors -f ${BASE_PATH}/pom.xml clean

test:         ## 执行所有单元测试
	$(BASE_PATH)/mvnw --batch-mode --errors -f ${BASE_PATH}/pom.xml clean test -D test=*Test -DfailIfNoTests=false

package:      ## 打包所有模块，SKIP_TEST=true可跳过测试
	$(BASE_PATH)/mvnw --batch-mode --errors --fail-at-end --update-snapshots -f ${BASE_PATH}/pom.xml clean package -D maven.test.skip=$(SKIP_TEST)

set-version:  ## 设置项目版本号，NEW_VERSION=xxxx
	sh ${BASE_PATH}/bin/set-version.sh $(NEW_VERSION)

install:      ## 安装所有模块到本地仓库，SKIP_TEST=true可跳过测试
	$(BASE_PATH)/mvnw --batch-mode --errors --fail-at-end --update-snapshots -f ${BASE_PATH}/pom.xml clean install -D maven.test.skip=$(SKIP_TEST)

.PHONY: deploy
deploy:       ## 部署所有模块到远程仓库
	$(BASE_PATH)/mvnw --batch-mode --errors --fail-at-end --update-snapshots -f ${BASE_PATH}/pom.xml deploy

# ----------------------------- maven >-----------------------------
