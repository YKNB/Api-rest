

function changeMode(mode:boolean){
  if (mode) mode=false;
  else mode=true;
}


function PrintText(title, size): string{
  let res = title;
  if (title.length > size){
    res = res.substring(0, size);
    res = res + '...';
    return res;
  }
  return title;
}
