var x;
function sub1() {
  document.write("x = " + x + "");
}
function sub2() {
  var x;
  x = 10;
  sub1();
}

x = 5;
sub2();
