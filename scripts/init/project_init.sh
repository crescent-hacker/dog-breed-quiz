#!/usr/bin/env sh

echo "On each commit run ktlintformat"
./gradlew addKtlintFormatGitPreCommitHook