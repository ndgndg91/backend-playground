# 현재 브랜치가 main의 조상을 포함하는지 확인
if git merge-base --is-ancestor origin/main HEAD; then
    echo "✅ 브랜치가 최신 main을 포함하고 있습니다. 안전합니다."
    exit 0
else
    echo "❌ 위험: 브랜치가 최신 main 을 포함하고 있지 않습니다!"
    echo "이 브랜치를 배포하기 전에 'git rebase origin/main'을 실행하여 최신 코드를 반영해주세요."
    exit 1 # 파이프라인 실패 처리
fi
