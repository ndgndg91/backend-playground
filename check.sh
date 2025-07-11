# main 브랜치의 최신 커밋을 가져옴
MAIN_HEAD=$(git rev-parse main)

# 현재 브랜치가 main의 조상을 포함하는지 확인
if git merge-base --is-ancestor $MAIN_HEAD HEAD; then
    echo "현재 브랜치는 main 브랜치의 최신 내용을 포함하고 있습니다. ✅"
else
    echo "현재 브랜치가 main 브랜치에서 분기되었습니다. 'git pull' 또는 'git rebase'를 고려해보세요. ❌"
fi