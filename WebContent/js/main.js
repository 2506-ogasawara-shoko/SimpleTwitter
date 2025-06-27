/**
 * つぶやきの削除を実施する前に
 * 確認ダイアログを表示
 */

$(function(){
    $("#delete").on("click", function() {
        alert("本当に削除しますか？");
    });
});
